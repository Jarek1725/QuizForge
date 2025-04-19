import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ExamDetailsDTO} from '../../openapi/tomaszewski/openapi';
import {NgIf} from '@angular/common';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-exam-attempt',
  imports: [
    FormsModule,
    NgIf,
    MatButton,
  ],
  templateUrl: './exam-attempt.component.html',
  styleUrl: './exam-attempt.component.scss'
})
export class ExamAttemptComponent {
  currentStep: number = 0;
  exam: ExamDetailsDTO|null = null;


  next() {
    // if (this.currentStep === 0) {
    // } else if (this.currentStep <= this.exam.questions.length) {
      // this.saveAnswer();
    // }
    // this.currentStep++;
  }


  get currentQuestion() {
    return null;
    // return this.exam.questions[this.currentStep - 1];
  }
}
