import { Component } from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {MatList, MatListItem} from '@angular/material/list';

@Component({
  selector: 'app-exam-list',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatListItem,
    MatList
  ],
  templateUrl: './exam-list.component.html',
  styleUrl: './exam-list.component.scss'
})
export class ExamListComponent {

}
