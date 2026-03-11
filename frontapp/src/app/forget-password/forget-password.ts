import { Component } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { FormsModule } from '@angular/forms'
import { ChangeDetectorRef } from '@angular/core'
import { Router } from '@angular/router'

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './forget-password.html',
  styleUrl: './forget-password.css'
})
export class ForgetPassword {

  constructor(
              private http: HttpClient,
              private router: Router,
              private cdr: ChangeDetectorRef
  ){}

  email = ""
  code = ""
  newPassword = ""
  confirmPassword = ""

  codeSent = false
  verified = false
  passwordMismatch = false
  isLoggedIn= false

  showMenu = false

  token = ""

  ngOnInit(){

    this.token = localStorage.getItem("token") || ""
    this.isLoggedIn = !!this.token
  }

  sendCode(){

    this.http.get(`http://localhost:8080/api/auth/forgetPassword?email=${this.email}`)
    .subscribe(()=>{
      alert("Verification code sent to your email")
      this.codeSent = true
    })
    this.cdr.detectChanges()

  }

  resetPassword(){

    if(this.newPassword !== this.confirmPassword){
    this.passwordMismatch = true
    return
  }

    this.http.post("http://localhost:8080/api/auth/resetPassword", {
      email: this.email,
      newPassword: this.newPassword,
      code:this.code
    })
    .subscribe({
      next: () =>{
      alert("Password reset successful")
      },
      error: ()=>{
      alert("Wrong code")
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
