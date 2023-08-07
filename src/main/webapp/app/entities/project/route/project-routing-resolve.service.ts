import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProject } from '../project.model';
import { ProjectService } from '../service/project.service';

export const projectResolve = (route: ActivatedRouteSnapshot): Observable<null | IProject> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProjectService)
      .find(id)
      .pipe(
        mergeMap((project: HttpResponse<IProject>) => {
          if (project.body) {
            return of(project.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default projectResolve;
