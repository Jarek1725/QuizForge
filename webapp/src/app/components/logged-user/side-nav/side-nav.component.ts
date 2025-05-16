import {Component} from '@angular/core';
import {MatDrawer} from '@angular/material/sidenav';
import {MatIcon} from '@angular/material/icon';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {MatIconAnchor} from '@angular/material/button';

@Component({
  selector: 'app-side-nav',
  imports: [
    MatDrawer,
    MatIcon,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.scss'
})
export class SideNavComponent {
}
