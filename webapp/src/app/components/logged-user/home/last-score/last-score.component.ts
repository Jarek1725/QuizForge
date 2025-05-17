import {Component, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatButton} from '@angular/material/button';
import {ActivatedRoute, Router} from '@angular/router';
import {AttemptService, AttemptSummaryDTO} from '../../../../openapi/tomaszewski/openapi';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-last-score',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatProgressSpinner,
    MatButton,
    DatePipe
  ],
  templateUrl: './last-score.component.html',
  styleUrl: './last-score.component.scss'
})
export class LastScoreComponent implements OnInit {
  attemptId!: number;
  attemptSummary: AttemptSummaryDTO | null = null;
  maxScore = 0;
  percentageScore = 0;
  passed: boolean = false;

  constructor(private attemptService: AttemptService, private router: Router) {
  }

  ngOnInit(): void {
    this.attemptService.getUserLastAttempts().subscribe({
      next: (attempts) => {
        console.log('Attempt:', attempts);
        this.attemptSummary = attempts[0];
        if (this.attemptSummary) {
          if (this.attemptSummary?.userAnswerDetails) {
            this.maxScore = this.attemptSummary.userAnswerDetails
              .map(q => q.question?.score ?? 0)
              .reduce((acc, curr) => acc + curr, 0);
          }

          this.attemptSummary?.userAnswerDetails?.forEach(e => {
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
            this.percentageScore = (score / this.maxScore) * 100;
          }
        }
      },
      error: (err) => {
        console.error('Błąd pobierania podejścia:', err);
      }
    })
  }

  goToExam(id: number | undefined) {
    if (id) {
      this.router.navigate(['/exams', id]).then(r =>
        console.log('Nawigacja do egzaminu zakończona pomyślnie:', r));
    }
  }
}
