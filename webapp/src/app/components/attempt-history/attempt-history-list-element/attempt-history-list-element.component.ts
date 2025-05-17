import {Component, Input, OnInit} from '@angular/core';
import {AttemptSummaryDTO} from '../../../openapi/tomaszewski/openapi';
import {DatePipe, NgClass, NgIf} from '@angular/common';

@Component({
  selector: 'app-attempt-history-list-element',
  imports: [
    DatePipe,
    NgClass,
    NgIf
  ],
  templateUrl: './attempt-history-list-element.component.html',
  styleUrl: './attempt-history-list-element.component.scss'
})
export class AttemptHistoryListElementComponent implements OnInit {
  maxScore = 0;
  passed: boolean = false;
  mode: string = 'Egzamin'

  @Input() attemptResult?: AttemptSummaryDTO;

  ngOnInit(): void {
    if (this.attemptResult?.userAnswerDetails) {
      this.maxScore = this.attemptResult.userAnswerDetails
        .map(q => q.question?.score ?? 0)
        .reduce((acc, curr) => acc + curr, 0);
    }

    const score = this.attemptResult?.score;
    const percentageToPass = this.attemptResult?.exam?.percentageToPass;

    if(!this.attemptResult?.isExam){
      this.mode = 'Powtórka'
    }

    if (
      score !== undefined &&
      percentageToPass !== undefined &&
      this.maxScore > 0
    ) {
      const requiredScore = (this.maxScore * percentageToPass) / 100;
      this.passed = score >= requiredScore;
    }
  }


}
