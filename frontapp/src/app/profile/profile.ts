import { Component, ChangeDetectorRef } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { FormsModule } from '@angular/forms'

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {

  constructor(private http: HttpClient){}

  token = ""

  user = {
    email: "",
    username: "",
    name: "",
    submissions: 0
  }

  newPassword = ""

  ngOnInit(){

    this.token = localStorage.getItem("token") || ""

    const headers = {
      Authorization: `Bearer ${this.token}`
    }

    this.http.get<any>("http://localhost:8080/api/user/profile", { headers })
    .subscribe(data => {
      this.user = data
    })

  }

  updateProfile(){

    const headers = {
      Authorization: `Bearer ${this.token}`
    }

    const body = {
      email: this.user.email,
      username: this.user.username
    }

    this.http.put("http://localhost:8080/api/user/updateProfile", body, { headers })
    .subscribe(() => {
      alert("Profile updated")
    })

  }

  changePassword(){

    const headers = {
      Authorization: `Bearer ${this.token}`
    }

    this.http.put(
      "http://localhost:8080/api/user/changePassword",
      { password: this.newPassword },
      { headers }
    ).subscribe(()=>{
      alert("Password changed")
      this.newPassword = ""
    })

  }

}