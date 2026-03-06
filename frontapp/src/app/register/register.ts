import { Component } from '@angular/core'
import { FormsModule } from '@angular/forms'
import { HttpClient } from '@angular/common/http'

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

  constructor(private http: HttpClient) {}

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

}