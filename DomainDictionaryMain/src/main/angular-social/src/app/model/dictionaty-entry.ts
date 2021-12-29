export class DictionaryEntry {
  id?: number;
  term?:String;
  definition?: String[];
  constructor(id: number,term:String,definition:String[]) {
    this.id = id;
    this.term = term;
    this.definition=definition;
  }
}
