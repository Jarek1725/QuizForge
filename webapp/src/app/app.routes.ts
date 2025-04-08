import {Routes} from '@angular/router';
import {HomeGuestComponentComponent} from './components/home-guest-component/home-guest-component.component';
import {HomeComponent} from './components/logged-user/home/home.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeGuestComponentComponent, canActivate: [],
    children:[
      {
        path: 'home',
        component: HomeComponent, canActivate: []
      }
    ]
  }


];
