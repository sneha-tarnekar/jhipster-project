import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProjectComponent } from './list/project.component';
import { ProjectDetailComponent } from './detail/project-detail.component';
import { ProjectUpdateComponent } from './update/project-update.component';
import ProjectResolve from './route/project-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const projectRoute: Routes = [
  {
    path: '',
    component: ProjectComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProjectDetailComponent,
    resolve: {
      project: ProjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProjectUpdateComponent,
    resolve: {
      project: ProjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProjectUpdateComponent,
    resolve: {
      project: ProjectResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default projectRoute;
