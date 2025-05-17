import {Component, OnInit} from '@angular/core';
import {AttemptService, AttemptSummaryDTO} from '../../openapi/tomaszewski/openapi';
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe, NgClass, NgForOf} from '@angular/common';
import {ExpendableBoxComponent} from './expendable-box/expendable-box.component';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-attempt-details',
  imports: [
    NgForOf,
    ExpendableBoxComponent,
    DatePipe,
    NgClass,
    MatButton,
  ],
  templateUrl: './attempt-details.component.html',
  styleUrl: './attempt-details.component.scss'
})
export class AttemptDetailsComponent implements OnInit {
  attemptId!: number;
  attemptSummary: AttemptSummaryDTO | null = null;
  maxScore = 0;
  passed: boolean = false;
  mode: string = 'Egzamin'

  constructor(private route: ActivatedRoute, private attemptService: AttemptService, private router: Router) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('attemptId');
    if (idParam) {
      this.attemptId = +idParam;
      this.attemptService.getAttemptById(this.attemptId).subscribe({
        next: (attempt) => {
          this.attemptSummary = attempt

          if (this.attemptSummary?.userAnswerDetails) {
            this.maxScore = this.attemptSummary.userAnswerDetails
              .map(q => q.question?.score ?? 0)
              .reduce((acc, curr) => acc + curr, 0);
          }

          if(!this.attemptSummary?.isExam){
            this.mode = 'Powtórka'
          }

          const score = this.attemptSummary?.score;
          const percentageToPass = this.attemptSummary?.exam?.percentageToPass;
          if (
            score !== undefined &&
            percentageToPass !== undefined &&
            this.maxScore > 0
          ) {
            const requiredScore = (this.maxScore * percentageToPass) / 100;
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

  tryAgain() {
    this.router.navigate(['/exams', this.attemptSummary?.exam?.id]).then(r =>
    console.log('Navigating to exam:', r))
  }
}
