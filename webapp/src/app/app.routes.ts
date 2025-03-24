import { Routes } from '@angular/router';
import {HomeGuestComponentComponent} from './components/home-guest-component/home-guest-component.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeGuestComponentComponent, canActivate: []
    }
];
