import {Rule} from "./rule";
import {SearchResource} from "./search-resource";

export class ElectronicDictionary extends SearchResource{
  author?: string;
  pathToFile = '';
  rule?: Rule;
}
