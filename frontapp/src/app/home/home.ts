import { HttpClient } from '@angular/common/http'
import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { Router } from '@angular/router'
import { RouterLink } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core'
import { Category } from '../category'
import { Item } from '../item'

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

  constructor(
              private http:HttpClient,
              private router: Router,
              private cdr: ChangeDetectorRef
            ){}

  searchText = ""
  categoryId = 0
  token = ""

  isLoggedIn = false
  showMenu = false

  items:Item[] = []
  categories:Category[] = []

  ngOnInit() {
    this.token = localStorage.getItem("token") || ""
    this.isLoggedIn = !!this.token

    this.http.get<Category[]>("http://localhost:8080/api/category/getAllCategories")
    .subscribe(data => {
      this.categories = data
      this.cdr.detectChanges()
    })
  }

  search(){
    this.http.get<Item[]>(`http://localhost:8080/api/item/searchItemsByCategory?categoryId=${this.categoryId}&query=${this.searchText}`)
    .subscribe(data => {
      this.items = data
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

  logout(){
    const headers = {
      Authorization: `Bearer ${this.token}`
    }
    this.http.post(`http://localhost:8080/api/user/logout`,"",{headers})
    .subscribe({
        error: (err) => {
          console.log("Logout failed", err)
          return
        }
      })
      this.isLoggedIn = false;
      localStorage.removeItem("token");
      this.router.navigate(["/login"])
  }

   toggleMenu(){
  this.showMenu = !this.showMenu
}

}