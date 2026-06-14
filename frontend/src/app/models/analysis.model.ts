export interface MigrationTask {
  category: string;
  impact: string;
  description: string;
  suggestion: string;
  codeSnippet: string;
}

export interface AnalysisResponse {
  readinessScore: number;
  totalIssues: number;
  issuesByCategory: { [key: string]: number };
  roadmap: MigrationTask[];
}
