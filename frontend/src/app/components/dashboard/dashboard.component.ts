import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AnalysisService } from '../../services/analysis.service';
import { AnalysisResponse } from '../../models/analysis.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule, MatTableModule, NgxChartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  analysisResult: AnalysisResponse | null = null;
  
  constructor(private api: AnalysisService) {}

  onFileSelected(e: any) {
    const file: File = e.target.files[0];
    if (file) {
      this.api.analyzePom(file).subscribe(res => this.analysisResult = res);
    }
  }

  downloadReport() {
    if (this.analysisResult) {
      this.api.exportPdf(this.analysisResult).subscribe(b => {
        const a = document.createElement('a');
        a.href = URL.createObjectURL(b);
        a.download = 'Migration-Blueprint.pdf';
        a.click();
      });
    }
  }
}
