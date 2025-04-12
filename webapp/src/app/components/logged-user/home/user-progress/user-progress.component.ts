import {Component, ViewChild} from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexDataLabels,
  ApexStroke,
  ApexTooltip,
  ApexXAxis,
  ChartComponent,
  NgApexchartsModule
} from 'ng-apexcharts';
import {MatCard, MatCardContent, MatCardHeader} from '@angular/material/card';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  tooltip: ApexTooltip;
  dataLabels: ApexDataLabels;
};

@Component({
  selector: 'app-user-progress',
  imports: [
    NgApexchartsModule,
    MatCardContent,
    MatCard,
    MatCardHeader
  ],
  templateUrl: './user-progress.component.html',
  styleUrl: './user-progress.component.scss'
})




export class UserProgressComponent {
  @ViewChild("chart") chart?: ChartComponent;
  public chartOptions: Partial<ChartOptions> | any;

  constructor() {
    this.chartOptions = {
      series: [
        {
          name: "Twoje wyniki",
          data: [31, 40, 28, 51, 42, 74, 82]
        },
        {
          name: "Wyniki innych użytkowników",
          data: [11, 32, 45, 32, 34, 52, 41]
        }
      ],
      chart: {
        toolbar:{
          show: false
        },
        height: '190px',
        type: "area"
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: "smooth"
      },
      xaxis: {
        type: "numeric",
      },
      tooltip: {
        x: {
          format: "dd/MM/yy HH:mm"
        }
      }
    };
  }

}
