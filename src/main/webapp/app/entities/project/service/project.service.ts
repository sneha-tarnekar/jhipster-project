import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProject, NewProject } from '../project.model';

export type PartialUpdateProject = Partial<IProject> & Pick<IProject, 'id'>;

export type EntityResponseType = HttpResponse<IProject>;
export type EntityArrayResponseType = HttpResponse<IProject[]>;

@Injectable({ providedIn: 'root' })
export class ProjectService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/projects');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(project: NewProject): Observable<EntityResponseType> {
    return this.http.post<IProject>(this.resourceUrl, project, { observe: 'response' });
  }

  update(project: IProject): Observable<EntityResponseType> {
    return this.http.put<IProject>(`${this.resourceUrl}/${this.getProjectIdentifier(project)}`, project, { observe: 'response' });
  }

  partialUpdate(project: PartialUpdateProject): Observable<EntityResponseType> {
    return this.http.patch<IProject>(`${this.resourceUrl}/${this.getProjectIdentifier(project)}`, project, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProject>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProject[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProjectIdentifier(project: Pick<IProject, 'id'>): string {
    return project.id;
  }

  compareProject(o1: Pick<IProject, 'id'> | null, o2: Pick<IProject, 'id'> | null): boolean {
    return o1 && o2 ? this.getProjectIdentifier(o1) === this.getProjectIdentifier(o2) : o1 === o2;
  }

  addProjectToCollectionIfMissing<Type extends Pick<IProject, 'id'>>(
    projectCollection: Type[],
    ...projectsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const projects: Type[] = projectsToCheck.filter(isPresent);
    if (projects.length > 0) {
      const projectCollectionIdentifiers = projectCollection.map(projectItem => this.getProjectIdentifier(projectItem)!);
      const projectsToAdd = projects.filter(projectItem => {
        const projectIdentifier = this.getProjectIdentifier(projectItem);
        if (projectCollectionIdentifiers.includes(projectIdentifier)) {
          return false;
        }
        projectCollectionIdentifiers.push(projectIdentifier);
        return true;
      });
      return [...projectsToAdd, ...projectCollection];
    }
    return projectCollection;
  }
}
