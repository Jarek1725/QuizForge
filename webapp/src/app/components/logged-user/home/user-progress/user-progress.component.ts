import {Component, OnInit, ViewChild} from '@angular/core';
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
import {AttemptService, ProgressDataDTO} from '../../../../openapi/tomaszewski/openapi';

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


export class UserProgressComponent implements OnInit {
  @ViewChild("chart") chart?: ChartComponent;
  public chartOptions: Partial<ChartOptions> | any;

  constructor(private attemptService: AttemptService) {
    this.chartOptions = {
      series: [],
      chart: {
        toolbar: {
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
      tooltip: {
        x: {
          format: "dd/MM/yy HH:mm"
        }
      }
    };
  }

  ngOnInit(): void {
    this.attemptService.getAttemptProgressData()
      .subscribe((data: ProgressDataDTO) => {
        this.chartOptions.series = [
          {
            name: "Twoje wyniki",
            data: data.progressData
          }
        ];
      });
  }

}
