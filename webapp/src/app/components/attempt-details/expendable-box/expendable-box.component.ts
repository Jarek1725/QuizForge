import {Component, Input, OnInit} from '@angular/core';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {QuestionDetailsDTO} from '../../../openapi/tomaszewski/openapi';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-expendable-box',
  imports: [
    MatFormField,
    MatInput,
    MatLabel,
    NgIf
  ],
  templateUrl: './expendable-box.component.html',
  styleUrl: './expendable-box.component.scss'
})
export class ExpendableBoxComponent implements OnInit {
  isHovered = false;
  @Input() questionDetailsDTO?: QuestionDetailsDTO;
  passed = true;

  ngOnInit(): void {
    console.log(this.questionDetailsDTO)
    this.questionDetailsDTO?.answers?.forEach(answer => {
      if (answer.isSelected != answer.isCorrect) {
        this.passed = false;
      }
    })
  }


}
