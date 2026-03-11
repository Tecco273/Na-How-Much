import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { HttpClient } from '@angular/common/http'
import { Router } from '@angular/router'

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  username = ""
  password = ""
  token = ""

  isEmail = false

  isLoggedIn= false
  showMenu = false



  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(){

    this.token = localStorage.getItem("token") || ""
    this.isLoggedIn = !!this.token
  }

  login(){

    this.isEmail = this.username.includes("@")
    const body = this.isEmail ? { email: this.username, password: this.password }
    : { username: this.username, password: this.password }

    this.http.post("http://localhost:8080/api/auth/login", body)
      .subscribe({
        next: (res) => {
          const token = (res as any).token

        localStorage.setItem("token", token)
        localStorage.setItem("userId", (res as any).userId)

        console.log("Login success")

        this.router.navigate(["/"])
        },
        error: (err) => {
          console.log("Login failed", err)
        }
      })

  }

  goToRegister(){
    this.router.navigate(["/register"])
  }

  forgetPassword(){
    this.router.navigate(["/forget-password"])
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