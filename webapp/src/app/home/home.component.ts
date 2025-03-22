import { Component } from '@angular/core';
import {ColorService} from '../services/color.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  randomColor: string;
  constructor(private colorService: ColorService) {
    this.randomColor = this.colorService.getRandomColor();
  }

}
