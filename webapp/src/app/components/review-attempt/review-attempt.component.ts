import {Component, OnInit} from '@angular/core';
import {ReviewService} from '../../openapi/tomaszewski/openapi';

@Component({
  selector: 'app-review-attempt',
  imports: [],
  templateUrl: './review-attempt.component.html',
  styleUrl: './review-attempt.component.scss'
})
export class ReviewAttemptComponent implements OnInit {
  constructor(private reviewService: ReviewService) {
  }

  ngOnInit(): void {

  }

}
