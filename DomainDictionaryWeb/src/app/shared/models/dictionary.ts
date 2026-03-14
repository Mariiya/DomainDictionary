export interface ElectronicDictionary {
  id: number;
  name: string;
  pathToFile: string;
  language: string;
  type: ResourceType;
  createdAt: string;
}

export enum ResourceType {
  SYSTEM_GENERAL = 'SYSTEM_GENERAL',
  SYSTEM_ENCYCLOPEDIC = 'SYSTEM_ENCYCLOPEDIC',
  GLOSSARY = 'GLOSSARY',
  DOMAIN = 'DOMAIN'
}

export interface SaveResourceRequest {
  name: string;
  language: string;
  type: ResourceType;
  entries: { term: string; definitions: string[] }[];
}
