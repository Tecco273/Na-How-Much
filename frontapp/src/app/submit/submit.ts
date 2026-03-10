import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { HttpClient } from '@angular/common/http'
import { Category } from '../category'
import { Item } from '../item'
import { Subject } from 'rxjs'
import { debounceTime, switchMap } from 'rxjs/operators'

@Component({
  selector:'app-submit',
  standalone:true,
  imports:[FormsModule],
  templateUrl:'./submit.html',
  styleUrl:'./submit.css'
})

export class Submit{

  token = ""
  userId = ""

 ngOnInit() {
  this.token = localStorage.getItem("token") || ""
  this.userId = localStorage.getItem("userId") || ""
  const headers = {
    Authorization: `Bearer ${this.token}`
  }
    // ITEM SEARCH DEBOUNCE
  this.itemSearchSubject.pipe(
    debounceTime(300),
    switchMap(query =>
      this.http.get<any[]>(`http://localhost:8080/api/item/searchItems?query=${query}`, { headers })
    )
  ).subscribe(data => {
    this.itemSuggestions = data
  })

  // CATEGORY SEARCH DEBOUNCE
  this.categorySearchSubject.pipe(
    debounceTime(300),
    switchMap(query =>
      this.http.get<any[]>(`http://localhost:8080/api/category/searchCategories?query=${query}`, { headers })
    )
  ).subscribe(data => {
    this.categorySuggestions = data
  })

}

itemSearchSubject = new Subject<string>()
categorySearchSubject = new Subject<string>()

  itemSearch = ""
  itemSuggestions: Item[] = []
  selectedItem: Item | null = null

  categorySearch = ""
  categorySuggestions: Category[] = []
  selectedCategory: Category | null = null


  receiptFile:any = null

  showNewItemPopup = false

  itemName = ""
  itemCategoryId = ""
  itemImage:any = null
  itemImageUrl = ""

  price = ""
  location = ""
  purchaseDate = ""

  constructor(private http:HttpClient){}

  onFileSelected(event:any){
  this.receiptFile = event.target.files[0]
  }

  openNewItemPopup(){
    this.showNewItemPopup = true
  }

  closeNewItemPopup(){
    this.showNewItemPopup = false
  }

  onItemImageSelected(event:any){
    this.itemImage = event.target.files[0]
    console.log("Selected image file:", this.itemImage)
  }

  saveNewItem(){

  this.uploadImage(this.itemImage).subscribe((res:any) => {

    const imageUrl = res.secure_url

    const body = {
      name: this.itemName,
      categoryId: this.itemCategoryId,
      imageUrl: imageUrl
    }

    const headers = {
      Authorization: `Bearer ${this.token}`
    }

    this.http.post(
      "http://localhost:8080/api/item/saveItem",
      body,
      { headers }
    ).subscribe({
      next:(res)=>{
        console.log("Item added", res)
      },
      error:(err)=>console.log("Failed to add item", err)
    })

  })

  this.showNewItemPopup = false
}

  uploadImage(file: File) {
    const formData = new FormData()

    formData.append("file", file)
    formData.append("upload_preset", "Nahowmuch items")

    return this.http.post<any>(
      "https://api.cloudinary.com/v1_1/dgtzmlixe/image/upload",
      formData
    )
  }

searchItems(){
  if(this.itemSearch.length < 2){
    this.itemSuggestions = []
    return
  }

  this.itemSearchSubject.next(this.itemSearch)
}

searchCategories(){
  if(this.categorySearch.length < 2){
    this.categorySuggestions = []
    return
  }

  this.categorySearchSubject.next(this.categorySearch)
}

selectItem(item: Item){

  this.selectedItem = item
  this.itemSuggestions = []
  this.itemSearch = ""

}

selectCategory(cat: Category){

  this.selectedCategory = cat
  this.categorySuggestions = []
  this.categorySearch = ""

}

submitPurchase(){
  this.uploadImage(this.receiptFile).subscribe((res:any) => {

    const imageUrl = res.secure_url

    const body = {
      userId: this.userId,
      itemId: this.selectedItem?.id,
      price: parseFloat(this.price),
      location: this.location,
      purchaseDate: this.purchaseDate,
      receiptImageUrl: imageUrl
    }

    const headers = {
      Authorization: `Bearer ${this.token}`
    }

    this.http.post(
      "http://localhost:8080/api/submission/saveSubmission",
      body,
      { headers }
    ).subscribe({
      next:(res)=>{
        console.log("submission added", res)
      },
      error:(err)=>console.log("Failed to add submission", err)
    })

  })
}

}