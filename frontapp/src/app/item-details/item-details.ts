import { Component } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { HttpClient } from '@angular/common/http'
import { CommonModule } from '@angular/common'
import { ChangeDetectorRef } from '@angular/core'
import { Router } from '@angular/router'
import { Item } from '../item'
import { Category } from '../category'

@Component({
  selector: 'app-item-details',
  standalone: true,
  imports:[CommonModule],
  templateUrl:'./item-details.html',
  styleUrl:'./item-details.css'
})
export class ItemDetails {

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private router: Router

  ){}

  item:Item|null =null
  submissions:any[]=[]
  category:Category|null=null

  isLoggedIn= false
  showMenu = false

  token = ""


  ngOnInit(){

    this.token = localStorage.getItem("token") || ""
    this.isLoggedIn = !!this.token

    const id = this.route.snapshot.paramMap.get("id")

    this.http.get<Item>(
      `http://localhost:8080/api/item/getItemById?id=${id}`
    ).subscribe(data=>{
      this.item = data
      this.http.get<Item>(
        `http://localhost:8080/api/category/getCategoryById?id=${data.categoryId}`
      ).subscribe(data2=>{
        this.category = data2
        this.cdr.detectChanges()
      })
      this.cdr.detectChanges()
    })

    this.http.get<any[]>(
      `http://localhost:8080/api/submission/getLast5ForItem?itemId=${id}`
    ).subscribe(data=>{
      this.submissions = data
      this.cdr.detectChanges()
    })

  }


  goLogin(){
    this.router.navigate(["/login"])
  }

  goProfile(){
    this.router.navigate(["/profile"])
  }

  addSubmission(){
    this.router.navigate(["/submit"])
  }

   toggleMenu(){
    this.showMenu = !this.showMenu
  }

  logout(){
    localStorage.removeItem("token");
    this.http.post(`http://localhost:8080/api/user/logout`,"")
    .subscribe({
        next: (res) => {
          this.isLoggedIn =false;
          this.router.navigate(["/"])
        },
        error: (err) => {
          console.log("Login failed", err)
        }
      })
  }

}