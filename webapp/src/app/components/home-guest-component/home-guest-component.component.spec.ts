import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeGuestComponentComponent } from './home-guest-component.component';

describe('HomeGuestComponentComponent', () => {
  let component: HomeGuestComponentComponent;
  let fixture: ComponentFixture<HomeGuestComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeGuestComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeGuestComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
