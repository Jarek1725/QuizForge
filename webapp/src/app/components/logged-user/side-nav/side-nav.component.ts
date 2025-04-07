import {Component} from '@angular/core';
import {MatDrawer} from '@angular/material/sidenav';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-side-nav',
  imports: [
    MatDrawer,
    MatIcon
  ],
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.scss'
})
export class SideNavComponent {
}
