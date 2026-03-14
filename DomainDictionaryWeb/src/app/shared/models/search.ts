export interface SearchResult {
  term: string;
  definitions: string[];
  source: string;
}

export interface ThesaurusEntry {
  term: string;
  relations: Relation[];
}

export interface Relation {
  synsets: string[];
  type: string;
  coef: number;
}
