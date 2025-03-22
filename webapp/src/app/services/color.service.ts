import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorService {
  private pastelColors: string[] = [
    '#FFB3BA', '#FFDFBA', '#FFFFBA', '#BAFFC9',
    '#BAE1FF', '#F7D1BA', '#E6BAF7', '#F5F5DC',
  ];

  getRandomColor(): string {
    return this.pastelColors[Math.floor(Math.random() * this.pastelColors.length)];
  }
}
