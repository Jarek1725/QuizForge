import {Component, OnInit} from '@angular/core';
import {AttemptService, AttemptSummaryDTO} from '../../openapi/tomaszewski/openapi';
import {ActivatedRoute} from '@angular/router';
import {DatePipe, NgClass, NgForOf} from '@angular/common';
import {ExpendableBoxComponent} from './expendable-box/expendable-box.component';

@Component({
  selector: 'app-attempt-details',
  imports: [
    NgForOf,
    ExpendableBoxComponent,
    DatePipe,
    NgClass
  ],
  templateUrl: './attempt-details.component.html',
  styleUrl: './attempt-details.component.scss'
})
export class AttemptDetailsComponent implements OnInit {
  attemptId!: number;
  attemptSummary: AttemptSummaryDTO | null = null;
  maxScore = 0;
  passed: boolean = false;

  constructor(private route: ActivatedRoute, private attemptService: AttemptService) {
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('attemptId');
    if (idParam) {
      this.attemptId = +idParam;
      this.attemptService.getAttemptById(this.attemptId).subscribe({
        next: (attempt) => {
          console.log('Attempt:', attempt);
          this.attemptSummary = attempt

          if (this.attemptSummary?.userAnswerDetails) {
            this.maxScore = this.attemptSummary.userAnswerDetails
              .map(q => q.question?.score ?? 0)
              .reduce((acc, curr) => acc + curr, 0);
          }

          this.attemptSummary?.userAnswerDetails?.forEach(e=>{
            console.log(e.question?.score)
          })

          const score = this.attemptSummary?.score;
          const percentageToPass = this.attemptSummary?.exam?.percentageToPass;
          console.log(this.attemptSummary?.userAnswerDetails)
          console.log(this.maxScore)
          if (
            score !== undefined &&
            percentageToPass !== undefined &&
            this.maxScore > 0
          ) {
            const requiredScore = (this.maxScore * percentageToPass) / 100;
            console.log(this.attemptSummary)
            console.log(requiredScore)
            this.passed = score >= requiredScore;
          }
        },
        error: (err) => {
          console.error('Błąd pobierania podejścia:', err);
        }
      });
    } else {
      console.error('Brak parametru attempt w ścieżce.');
    }
  }
}
