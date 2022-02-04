import {Rule} from "./rule";

export class ElectronicDictionary {
  id?: number;
  name?: string;
  author?: string;
  pathToFile = '';
  type?: string;
  sybtype?: string;
  rule?: Rule;
}
