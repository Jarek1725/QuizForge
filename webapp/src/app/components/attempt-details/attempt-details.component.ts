import {Component, OnInit} from '@angular/core';
import {AttemptService, AttemptSummaryDTO} from '../../openapi/tomaszewski/openapi';
import {ActivatedRoute} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {NgForOf} from '@angular/common';
import {ExpendableBoxComponent} from './expendable-box/expendable-box.component';

@Component({
  selector: 'app-attempt-details',
  imports: [
    MatButton,
    NgForOf,
    ExpendableBoxComponent
  ],
  templateUrl: './attempt-details.component.html',
  styleUrl: './attempt-details.component.scss'
})
export class AttemptDetailsComponent implements OnInit {
  attemptId!: number;
  attemptSummary: AttemptSummaryDTO | null = null;

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
