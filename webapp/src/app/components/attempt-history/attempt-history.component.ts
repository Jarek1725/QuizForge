import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AttemptService, AttemptSummaryDTO} from '../../openapi/tomaszewski/openapi';
import {NgForOf} from '@angular/common';
import {
  AttemptHistoryListElementComponent
} from './attempt-history-list-element/attempt-history-list-element.component';

@Component({
  selector: 'app-attempt-history',
  imports: [
    NgForOf,
    AttemptHistoryListElementComponent
  ],
  templateUrl: './attempt-history.component.html',
  styleUrl: './attempt-history.component.scss'
})
export class AttemptHistoryComponent implements OnInit {

  attempts: Array<AttemptSummaryDTO> = [];

  constructor(private attemptService: AttemptService, private router: Router) {
  }


  ngOnInit(): void {
    this.attemptService.getUserLastAttempts().subscribe({
      next: (attempts) => {
        this.attempts = attempts
      },
      error: (err) => {
        console.error('Błąd pobierania podejścia:', err);
      }
    })
  }

  onAttemptClick(a: AttemptSummaryDTO) {
    this.router.navigate(['/attempt', a.attemptId]).then(r => {
      console.log('Navigating to attempt:', r);
    }).catch(err => {
      console.error('Error navigating to attempt:', err);
    });
  }
}
