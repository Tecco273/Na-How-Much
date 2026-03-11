import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { HttpClient } from '@angular/common/http'
import { Router } from '@angular/router'

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  username = ""
  firstName = ""
  lastName = ""
  password = ""
  email = ""
  confirmPassword = ""

  passwordMismatch = false
  invalidUsername = false

  isLoggedIn= false
  showMenu = false
  token = ""

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(){

    this.token = localStorage.getItem("token") || ""
    this.isLoggedIn = !!this.token
  }

  checkPasswords(){
    this.passwordMismatch = this.password !== this.confirmPassword
  }

  checkUsername(){
      this.invalidUsername = this.username.includes('@')
  }

  register(){

  if(this.password !== this.confirmPassword){
    this.passwordMismatch = true
    return
  }

  this.checkUsername()

  if(this.invalidUsername){
    return
  }

  this.passwordMismatch = false

  const body = {
    username:this.username,
    email: this.email,
    firstName: this.firstName,
    lastName: this.lastName,
    password: this.password
  }

  this.http.post("http://localhost:8080/api/auth/signup", body)
    .subscribe({
      next: (res) => {
        console.log("Register success", res)
      },
      error: (err) => {
        console.log("Register failed", err)
      }
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