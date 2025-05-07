import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {ExamDetailsDTO, ExamService} from '../../openapi/tomaszewski/openapi';
import {ActivatedRoute} from '@angular/router';
import {MatCardContent} from '@angular/material/card';
import {MatListOption, MatSelectionList} from '@angular/material/list';
import {MatRadioButton, MatRadioGroup} from '@angular/material/radio';

@Component({
  selector: 'app-exam-attempt',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatCardContent,
    MatSelectionList,
    MatListOption,
    MatRadioGroup,
    MatRadioButton,
  ],
  templateUrl: './exam-attempt.component.html',
  styleUrls: ['./exam-attempt.component.scss']
})
export class ExamAttemptComponent implements OnInit {
  currentStep = 0;
  examDetails: ExamDetailsDTO | null = null;
  selectedAnswer: any = null;
  examId!: number;

  constructor(private route: ActivatedRoute, private examService: ExamService) {
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('examId');
    if (idParam) {
      this.examId = +idParam;
      this.examService.getExamById(this.examId).subscribe({
        next: (exam) => {
          console.log('Exam:', exam);
          this.examDetails = exam
        },
        error: (err) => {
          console.error('Błąd pobierania egzaminu:', err);
        }
      });
    } else {
      console.error('Brak parametru examId w ścieżce.');
    }
  }

  next(): void {
    const questions = this.examDetails?.questions;
    if (!questions) return;

    if (this.currentStep < questions.length) {
      this.saveAnswer();
      this.currentStep++;
    }
  }

  get questionCount(): number {
    return this.examDetails?.questions?.length ?? 0;
  }

  saveAnswer(): void {
    console.log('Zapisano odpowiedź:', this.selectedAnswer);
  }

  get currentQuestion() {
    if (
      this.examDetails?.questions &&
      this.currentStep > 0 &&
      this.currentStep <= this.examDetails.questions.length
    ) {
      return this.examDetails.questions[this.currentStep - 1];
    }
    return null;
  }

  prev() {
    const questions = this.examDetails?.questions;
    if (!questions) return;

    if (this.currentStep < questions.length) {
      this.saveAnswer();
      this.currentStep--;
    }
  }
}
