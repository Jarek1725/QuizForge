import {Component, OnInit} from '@angular/core';
import {ExamDTO, ExamService} from '../../openapi/tomaszewski/openapi';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {NgForOf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-exam-list-panel',
  imports: [
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatFormField,
    MatButton,
    FormsModule,
    NgForOf,
  ],
  templateUrl: './exam-list-panel.component.html',
  styleUrl: './exam-list-panel.component.scss'
})
export class ExamListPanelComponent implements OnInit {
  exams: ExamDTO[] = [];
  university: string = '';
  category: string = '';
  name: string = '';

  constructor(private examApi: ExamService, private router: Router) {
  }

  ngOnInit(): void {
    this.getExams()
  }

  filterList() {
    this.getExams();
  }

  getExams() {
    this.examApi.getExams(this.name, this.category, this.university, 500).subscribe({
      next: (data) => this.exams = data,
      error: (err) => console.error('Błąd podczas pobierania egzaminów', err)
    });
  }

  goToExam(id: number | undefined) {
    if (id) {
      this.router.navigate(['/exams', id]).then(r =>
        console.log('Nawigacja do egzaminu zakończona pomyślnie:', r));
    }
  }
}
