import { Component } from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-last-score',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatProgressSpinner,
    MatButton
  ],
  templateUrl: './last-score.component.html',
  styleUrl: './last-score.component.scss'
})
export class LastScoreComponent {

}
