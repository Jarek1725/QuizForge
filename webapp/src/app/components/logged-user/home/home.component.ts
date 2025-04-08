import {Component} from '@angular/core';
import {LastScoreComponent} from './last-score/last-score.component';
import {UserProgressComponent} from './user-progress/user-progress.component';
import {ExamListComponent} from '../../shared/exam-list/exam-list.component';

@Component({
  selector: 'app-home',
  imports: [
    LastScoreComponent,
    UserProgressComponent,
    ExamListComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
