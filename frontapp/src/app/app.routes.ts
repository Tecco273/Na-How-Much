import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Register } from './register/register';
import { Home } from './home/home';
import { Submit } from './submit/submit';
import { Profile } from './profile/profile';

export const routes: Routes = [
  { path: "login", component: Login },
  { path: "", component: Home },
  { path: "register", component: Register },
  { path: "submit", component: Submit },
  { path: "profile", component: Profile }
];
