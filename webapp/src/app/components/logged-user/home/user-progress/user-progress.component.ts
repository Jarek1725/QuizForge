import { Component } from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from "@angular/material/card";

@Component({
  selector: 'app-user-progress',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader
  ],
  templateUrl: './user-progress.component.html',
  styleUrl: './user-progress.component.scss'
})
export class UserProgressComponent {

}
