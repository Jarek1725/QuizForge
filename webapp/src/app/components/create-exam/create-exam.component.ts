import {Component} from '@angular/core';
import {CreateAnswerDTO, CreateExamDTO, CreateQuestionDTO, ExamService} from '../../openapi/tomaszewski/openapi';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {MatCheckbox} from '@angular/material/checkbox';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-exam',
  imports: [
    FormsModule,
    NgForOf,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatFormField,
    MatButton,
    MatIcon,
    MatSelect,
    MatOption,
    MatCheckbox,
  ],
  templateUrl: './create-exam.component.html',
  styleUrl: './create-exam.component.scss'
})
export class CreateExamComponent {
  createExamDTO: CreateExamDTO = {
    questions: []
  };
  categoryInput: string = '';

  constructor(private examService: ExamService, private router: Router) {
  }

  addQuestion(): void {
    const newQuestion: CreateQuestionDTO = {
      content: '',
      type: CreateQuestionDTO.TypeEnum.SingleChoice,
      answers: [],
      score: 1
    };
    this.createExamDTO.questions?.push(newQuestion);
  }

  addCategory(): void {
    const category = this.categoryInput.trim();
    if (category && !this.createExamDTO.categories?.includes(category)) {
      this.createExamDTO.categories = this.createExamDTO.categories || [];
      this.createExamDTO.categories.push(category);
    }
    this.categoryInput = '';
  }

  removeCategory(index: number): void {
    this.createExamDTO.categories?.splice(index, 1);
  }

  addAnswerToQuestion(question: CreateQuestionDTO): void {
    const newAnswer: CreateAnswerDTO = {
      text: '',
      isCorrect: false
    };
    question.answers = question.answers || [];
    question.answers.push(newAnswer);
  }

  removeQuestion(index: number): void {
    this.createExamDTO.questions?.splice(index, 1);
  }

  removeAnswerFromQuestion(question: CreateQuestionDTO, answerIndex: number): void {
    question.answers?.splice(answerIndex, 1);
  }

  submitExam(): void {
    console.log('Exam to submit:', this.createExamDTO);
    this.examService.createExam(this.createExamDTO).subscribe({
      next: (response) => {
        this.router.navigate(['/exams/' + response]);
      },
      error: (error) => {
        console.error('Error creating exam:', error);
      }
    });
  }

  protected readonly CreateQuestionDTO = CreateQuestionDTO;
}
