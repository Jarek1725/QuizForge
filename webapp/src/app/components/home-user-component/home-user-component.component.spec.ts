import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeUserComponentComponent } from './home-user-component.component';

describe('HomeUserComponentComponent', () => {
  let component: HomeUserComponentComponent;
  let fixture: ComponentFixture<HomeUserComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeUserComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeUserComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
