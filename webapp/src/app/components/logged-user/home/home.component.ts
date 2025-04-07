import {Component} from '@angular/core';
import {HeaderComponent} from '../../header/header.component';
import {SideNavComponent} from "../side-nav/side-nav.component";

@Component({
  selector: 'app-home',
    imports: [
        HeaderComponent,
        SideNavComponent
    ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
