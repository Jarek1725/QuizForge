import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {
  AttemptService,
  ExamDetailsDTO,
  ExamService,
  StartAttemptResponseDTO,
  SubmitAttemptDTO,
  StartAttemptDTO
} from '../../openapi/tomaszewski/openapi';
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
  examDetails: StartAttemptResponseDTO | null = null;
  selectedAnswer: any = null;
  selectedAnswersMultiple: number[] = [];
  examId!: number;
  submitAttemptDTO: SubmitAttemptDTO = {};

  constructor(private route: ActivatedRoute,
              private examService: ExamService,
              private attemptService: AttemptService) {
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('examId');
    if (idParam) {
      this.examId = +idParam;
      const startAttemptDTO: StartAttemptDTO = {examId: this.examId};
      this.attemptService.startAttempt(startAttemptDTO).subscribe({
        next: (exam) => {
          this.examDetails = exam
          this.submitAttemptDTO = {
            attemptId: exam.attemptId,
            answers: []
          };
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
    this.saveAnswer();

    if (this.currentStep < questions.length) {
      this.currentStep++;
    }
  }

  get questionCount(): number {
    return this.examDetails?.questions?.length ?? 0;
  }

  saveAnswer(): void {
    if (!this.submitAttemptDTO.answers) {
      this.submitAttemptDTO.answers = [];
    }
    if (this.currentQuestion != null && this.currentQuestion.answers != null) {
      if (this.currentQuestion.type === 'SINGLE_CHOICE') {
        if (this.selectedAnswer?.id) {
          this.submitAttemptDTO.answers.push(this.selectedAnswer.id);
        }
      } else if (this.currentQuestion.type === 'MULTIPLE_CHOICE') {
        this.submitAttemptDTO.answers.push(...this.selectedAnswersMultiple);
      }
    }
    console.log(this.submitAttemptDTO)
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

  submitAttempt() {
    this.saveAnswer();
    this.attemptService.submitAttempt(this.submitAttemptDTO).subscribe({
      next: (response) => {
        console.log('Wynik egzaminu:', response);
      },
      error: (err) => {
        console.error('Błąd podczas przesyłania odpowiedzi:', err);
      }
    });
  }
}
