package domain

type GeoLevel string

const (
	LevelNuts0       GeoLevel = "NUTS0"
	LevelNuts1       GeoLevel = "NUTS1"
	LevelNuts2       GeoLevel = "NUTS2"
	LevelNuts3       GeoLevel = "NUTS3"
	LevelLAU         GeoLevel = "LAU"
	LevelDistrict    GeoLevel = "DISTRICT"
	LevelCensusBlock GeoLevel = "CENSUS_BLOCK"
)

type GeoRegion struct {
	Code       string     `json:"code" gorm:"primaryKey"`
	Name       string     `json:"name"`
	Level      GeoLevel   `json:"level" gorm:"index"`
	ParentCode *string    `json:"parent_code"`
	Parent     *GeoRegion `json:"-" gorm:"foreignKey:ParentCode;references:Code"`
	Geometry   string     `json:"geometry,omitempty" gorm:"type:geometry(Polygon,4326)"`
}
