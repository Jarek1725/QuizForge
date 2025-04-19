import {Routes} from '@angular/router';
import {HomeGuestComponentComponent} from './components/home-guest-component/home-guest-component.component';
import {HomeComponent} from './components/logged-user/home/home.component';
import {ExamDetailsComponent} from './components/exam-details/exam-details.component';
import {ExamAttemptComponent} from './components/exam-attempt/exam-attempt.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeGuestComponentComponent, canActivate: [],
    children:[
      {
        path: 'home',
        component: HomeComponent, canActivate: []
      },
      {
        path: 'exams/:examId',
        component: ExamDetailsComponent, canActivate: []
      },
      {
        path: 'exams/:examId/attempt',
        component: ExamAttemptComponent, canActivate: []
      }
    ]
  }
];
