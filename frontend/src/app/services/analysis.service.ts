import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnalysisResponse } from '../models/analysis.model';
@Injectable({ providedIn: 'root' })
export class AnalysisService {
  private apiUrl = 'http://localhost:8080/api/modernize';
  constructor(private http: HttpClient) {}
  analyzePom(file: File): Observable<AnalysisResponse> {
    const formData = new FormData(); formData.append('file', file);
    return this.http.post<AnalysisResponse>(`${this.apiUrl}/analyze`, formData);
  }
  exportPdf(data: AnalysisResponse): Observable<Blob> {
    return this.http.post(`${this.apiUrl}/export`, data, { responseType: 'blob' });
  }
}
