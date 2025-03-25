import { Component } from '@angular/core';
import {NgOptimizedImage} from '@angular/common';
import {MatButton } from '@angular/material/button';

@Component({
  selector: 'app-header',
  imports: [
      MatButton
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

}
