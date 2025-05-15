import {Component, Input, OnInit} from '@angular/core';
import {AnswerDetailsDTO, QuestionDetailsDTO} from '../../../openapi/tomaszewski/openapi';
import {NgClass, NgForOf, NgIf, NgStyle} from '@angular/common';
import {MatCheckbox} from '@angular/material/checkbox';

@Component({
  selector: 'app-expendable-box',
  imports: [
    NgIf,
    NgForOf,
    NgClass,
    MatCheckbox,
    NgStyle
  ],
  templateUrl: './expendable-box.component.html',
  styleUrl: './expendable-box.component.scss'
})
export class ExpendableBoxComponent implements OnInit {
  isHovered = false;
  @Input() questionDetailsDTO?: QuestionDetailsDTO;
  passed = true;

  ngOnInit(): void {
    this.questionDetailsDTO?.answers?.forEach(answer => {
      if (answer.isSelected != answer.isCorrect) {
        this.passed = false;
      }
    })
  }

  getExpandedHeight(): string {
    const baseHeight = 76;
    const bottomMargin = 10;
    const answerHeight = 70;
    const answerCount = this.questionDetailsDTO?.answers?.length || 0;
    return `${bottomMargin + baseHeight + answerCount * answerHeight}px`;
  }

  getHintText(answer: AnswerDetailsDTO) {
    if (answer.isCorrect && answer.isSelected) return 'Poprawna odpowiedź';
    if (answer.isCorrect && !answer.isSelected) return 'Nie zaznaczona poprawna';
    if (!answer.isCorrect && answer.isSelected) return 'Błędna odpowiedź';
    return '';
  }
}
