package domain

import "github.com/google/uuid"

type POICategory string

const (
	CategoryTransport POICategory = "TRANSPORT"
	CategoryEducation POICategory = "EDUCATION"
	CategorySupply    POICategory = "SUPPLY"
	CategoryLeisure   POICategory = "LEISURE"
	CategoryOther     POICategory = "OTHER"
)

type POIProperties map[string]any

type PointOfInterest struct {
	ID   uuid.UUID `json:"id" gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	Name string    `json:"name"`

	Category    POICategory   `json:"category"`
	SubCategory string        `json:"sub_category"`
	Properties  POIProperties `json:"properties"`

	Location string `json:"location"`
}
