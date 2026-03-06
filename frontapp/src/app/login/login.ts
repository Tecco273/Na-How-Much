import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { HttpClient } from '@angular/common/http'
import { Router } from '@angular/router'
import { email } from '@angular/forms/signals'

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

  isEmail = false

  constructor(private http: HttpClient, private router: Router) {}

  login(){

    this.isEmail = this.username.includes("@")
    const body = this.isEmail ? { email: this.username, password: this.password }
    : { username: this.username, password: this.password }

    this.http.post("http://localhost:8080/api/auth/login", body)
      .subscribe({
        next: (res) => {
          console.log("Login success", res)
        },
        error: (err) => {
          console.log("Login failed", err)
        }
      })

  }

  goToRegister(){
    this.router.navigate(["/register"])
  }

}