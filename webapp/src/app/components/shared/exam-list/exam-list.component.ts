import {Component, OnInit} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';
import {MatList, MatListItem} from '@angular/material/list';
import {ExamDTO, ExamService} from '../../../openapi/tomaszewski/openapi';
import {NgForOf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-exam-list',
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatListItem,
    MatList,
    NgForOf,
    RouterLink
  ],
  templateUrl: './exam-list.component.html',
  styleUrl: './exam-list.component.scss'
})
export class ExamListComponent implements OnInit{
  exams: ExamDTO[] = [];

  constructor(private examApi: ExamService) {}

  ngOnInit(): void {
    this.examApi.getExams().subscribe({
      next: (data) => this.exams = data,
      error: (err) => console.error('Błąd podczas pobierania egzaminów', err)
    });
  }
}
