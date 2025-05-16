import {Routes} from '@angular/router';
import {HomeGuestComponentComponent} from './components/home-guest-component/home-guest-component.component';
import {HomeComponent} from './components/logged-user/home/home.component';
import {ExamDetailsComponent} from './components/exam-details/exam-details.component';
import {ExamAttemptComponent} from './components/exam-attempt/exam-attempt.component';
import {CreateExamComponent} from './components/create-exam/create-exam.component';
import {AttemptDetailsComponent} from './components/attempt-details/attempt-details.component';
import {AttemptHistoryComponent} from './components/attempt-history/attempt-history.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeGuestComponentComponent, canActivate: [],
    children: [
      {
        path: 'home',
        component: HomeComponent, canActivate: []
      },
      {
        path: 'exams/create',
        component: CreateExamComponent,
        canActivate: []
      },
      {
        path: 'exams/:examId/attempt',
        component: ExamAttemptComponent,
        canActivate: []
      },
      {
        path: 'exams/:examId',
        component: ExamDetailsComponent,
        canActivate: []
      },
      {
        path: 'attempt/history',
        component: AttemptHistoryComponent,
        canActivate: []
      },
      {
        path: 'attempt/:attemptId',
        component: AttemptDetailsComponent,
        canActivate: []
      }
    ]
  }
];
