import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {ExamDetailsDTO, ExamService, UserExamStatsDTO} from '../../openapi/tomaszewski/openapi';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-exam-details',
  imports: [
    MatButton,
    RouterLink,
  ],
  templateUrl: './exam-details.component.html',
  styleUrl: './exam-details.component.scss'
})
export class ExamDetailsComponent implements OnInit {
  examId!: number;
  examDetails: ExamDetailsDTO | null = null;
  examStats: UserExamStatsDTO | null = null;
  avgScore: string | null = null;
  totalAttempts: number | null = null;
  lastScore: string | null = null;
  questionCount: number | null = null;

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

      this.examService.getUserExamStats(this.examId).subscribe({
        next: (stats) => {
          console.log('User exam stats:', stats);
          this.examStats = stats;
          this.avgScore = (stats.averageScore * 100).toFixed(0);
          this.totalAttempts = stats.totalAttempts;
          this.lastScore = (stats.lastScore * 100).toFixed(0);
          this.questionCount = stats  .questionPoolSize;
        },
        error: (err) => {
          console.error('Błąd pobierania statystyk egzaminu:', err);
        }
      })
    } else {
      console.error('Brak parametru examId w ścieżce.');
    }
  }

}
